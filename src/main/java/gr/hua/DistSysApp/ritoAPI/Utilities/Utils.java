package gr.hua.DistSysApp.ritoAPI.Utilities;

import gr.hua.DistSysApp.ritoAPI.Models.Entities.Request;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.RequestResults;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.SubscriptionRequest;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.SubscriptionRequestsResults;
import gr.hua.DistSysApp.ritoAPI.Repositories.RequestRepository;
import gr.hua.DistSysApp.ritoAPI.Repositories.RequestResultsRepository;
import gr.hua.DistSysApp.ritoAPI.Repositories.SubscriptionRequestRepository;
import gr.hua.DistSysApp.ritoAPI.Repositories.SubscriptionRequestResultsRepository;

public class Utils {


    public static boolean isExistingPendingRequest(int userId , String requestType, RequestRepository requestRepository, RequestResultsRepository requestResultsRepository){


        Request testRequest = requestRepository.findRequestByUseridAndRequestTypeOrdered(userId, requestType);
        if ( testRequest == null) {
            return false;
        }
        int existentRequestId = testRequest.getRequest_id();
        RequestResults pendingRequestResult = requestResultsRepository.findRequestResultsByRequest_id(existentRequestId);
        if (pendingRequestResult.getRequest_status().equalsIgnoreCase("PENDING")){
            return true;
        }else return false;
    }

    public static boolean isExistingSubscriptionPendingRequest(int userId , String requestType, SubscriptionRequestRepository subscriptionRequestRepository, SubscriptionRequestResultsRepository subscriptionRequestResultsRepository){

        SubscriptionRequest subscriptionRequest =  subscriptionRequestRepository.findSubscriptionRequestByUseridAndRequestTypeOrdered(userId,requestType);
        if ( subscriptionRequest == null) {
            return false;
        }
        int existentRequestId = subscriptionRequest.getSubscription_request_id();
        SubscriptionRequestsResults pendingRequestResult = subscriptionRequestResultsRepository.findSubscriptionRequestResultsByRequest_id(existentRequestId);
        if (pendingRequestResult.getRequest_status().equalsIgnoreCase("Pending")){
            return true;
        }else return false;
    }
}
