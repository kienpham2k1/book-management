package com.springboot.borrowingservice.command.saga;

import com.springboot.borrowingservice.command.command.DeleteBorrowCommand;
import com.springboot.borrowingservice.command.event.BorrowCreatedEvent;
import com.springboot.borrowingservice.command.event.BorrowDeletedEvent;
import com.springboot.borrowingservice.command.event.BorrowingUpdateBookReturnEvent;
import com.springboot.commonservice.command.RollBackStatusBookCommand;
import com.springboot.commonservice.command.UpdateStatusBookCommand;
import com.springboot.commonservice.event.BookUpdateStatusEvent;
import com.springboot.commonservice.model.BookResponseCommonModel;
import com.springboot.commonservice.model.EmployeeResponseCommonModel;
import com.springboot.commonservice.query.GetDetailsBookQuery;
import com.springboot.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event) {
        System.out.println("BorrowCreatedEvent in Saga for BookId : " + event.getBookId() + " : EmployeeId :  " + event.getEmployeeId());
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseModel = queryGateway.query(getDetailsBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if (bookResponseModel.isReady()) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false, event.getEmployeeId(), event.getId());
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Sach Da co nguoi Muon");
            }
        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());

            System.out.println(e);
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handle(BookUpdateStatusEvent event)  {
        System.out.println("BookUpdateStatusEvent in Saga for BookId : "+event.getBookId());
        try {
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(event.getEmployeeId());

            EmployeeResponseCommonModel employeeResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                                    ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                            .join();
            if(employeeResponseCommonModel.getIsDisciplined()==true) {
                throw new Exception("Nhan vien bi ky luat");
            }else {
//                commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(), event.getEmployeeId(), "Da muon sach thanh cong !"));
                SagaLifecycle.end();
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
            rollBackBookStatus(event.getBookId(),event.getEmployeeId(),event.getBorrowId());
        }
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BorrowDeletedEvent event) {
        System.out.println("BorrowDeletedEvent in Saga for Borrowing Id : {} " + event.getId());
        SagaLifecycle.end();
    }

    private void rollBackBorrowRecord(String id) {
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true, employeeId, borrowId);
        rollBackBorrowRecord(borrowId);
        commandGateway.sendAndWait(command);
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingUpdateBookReturnEvent event) {
        System.out.println("BorrowingUpdateBookReturnEvent in Saga for BookId : " + event.getBookId() + " : EmployeeId :  " + event.getEmployeeId());
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            //notification kafka in here
            UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), true, event.getEmployeeId(), event.getId());
            commandGateway.sendAndWait(command);
            SagaLifecycle.end();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
