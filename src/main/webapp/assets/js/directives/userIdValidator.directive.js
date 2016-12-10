// An example of server side validation.
// Learn more about formatters and parsers - http://stackoverflow.com/questions/22841225/ngmodel-formatters-and-parsers
(function() {
    "use strict";
    
    angular.module("ngContacts").directive("userIdValidator", userIdValidator);

    userIdValidator.$inject = ["$log", "contactService", "$q"];
    
    function userIdValidator($log, contactService, $q) {
        var logger = $log.getInstance("userIdValidator");
        
        var directive = {
            require: "ngModel",
            link: function(scope, element, attrs, ngModel) {
                ngModel.$asyncValidators.userId = function(modelValue, viewValue) {
                    logger.log("Validating user id");
                    var inputValue = modelValue || viewValue;
                    var currContactId = attrs.userIdValidatorCurrContactId;
                    logger.log("inputValue: ", inputValue);
                    logger.log("currContactId: ", currContactId);
                    var deferred = $q.defer();
                    
                    contactService.doesUserIdExist(inputValue, currContactId)
                        .then(function(data) {
                            if (data.status == "SUCCESS") {
                                if (data.data.userIdExists) {
                                    logger.log("The user id already exists");
                                    deferred.reject();
                                }
                                else {
                                    logger.log("The user id does not exist");
                                    deferred.resolve();
                                }                                
                            }
                            else {
                                // Server side validation using custom error codes
                                var splitArr = data.errorMsgs[0].split(":");
                                logger.log(splitArr[1]);
                                ngModel.$setValidity(splitArr[0], false);
                                deferred.reject();
                            }
                        })
                        .catch(function(error) {
                            logger.log("Unable to check user id due to an unknown error");
                            deferred.reject();
                        });
                    
                    return deferred.promise;
                };
                
                // Resets the custom validation flags
                ngModel.$parsers.push(function(viewValue) {
                    ngModel.$setValidity("userId", true); // This should be the name of the validator
                    ngModel.$setValidity("1001", true);
                    ngModel.$setValidity("1002", true);
                    ngModel.$setValidity("1003", true);
                    return viewValue;
                });
            }
        };

        return directive;
    };
    
})();
