package gr.hua.DistSysApp.ritoAPI.Repositories;

import gr.hua.DistSysApp.ritoAPI.Models.Entities.Request;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT * FROM Request ",nativeQuery = true)
    List<Request> getAllRequests();

    @Query(value = "SELECT * FROM Request WHERE request_id=? AND user_id=?",nativeQuery = true)
    Request findRequestByRequest_idAndUserid(int requestId, int userId);

    @Query(value = "SELECT * FROM Request WHERE request_id=?",nativeQuery = true)
    Request findRequestByRequest_id(int requestId);

    @Query(value = "SELECT * FROM Request WHERE created_at=?",nativeQuery = true)
    Request findRequestByTimestamp(Timestamp timestamp);

    @Query(value = "SELECT request_id FROM Request WHERE user_id=?1 AND request_type=?2",nativeQuery = true)
    int findRequestIDByUseridAndRequestType(int UserId, String RequestType);

    @Query(value = "SELECT request_id FROM Request WHERE user_id=?1 AND request_type=?2 ORDER BY created_at DESC LIMIT 1",nativeQuery = true)
    int findRequestIDByUseridAndRequestTypeOrdered(int UserId, String RequestType);

    @Query(value = "SELECT * FROM Request WHERE user_id=?1 AND request_type=?2 ORDER BY created_at DESC LIMIT 1",nativeQuery = true)
    Request findRequestByUseridAndRequestTypeOrdered(int UserId, String RequestType);

    @Query(value = "SELECT * FROM Request WHERE user_id=?",nativeQuery = true)
    List <Request> findRequestByUserid(int UserId);

    @Query(value = "SELECT * FROM Request WHERE request_type=?1",nativeQuery = true)
    List <Request> findRequestsByRequestType(String RequestType);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Request SET request_type=?1,request_body=?2 WHERE request_id=?3",nativeQuery = true)
    Integer updateRequest_Accept(String request_type, String request_body,int requestId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Request SET request_type=?1 WHERE request_id=?2",nativeQuery = true)
    Integer updateRequest_Deny(String request_type,int request_id);
}
