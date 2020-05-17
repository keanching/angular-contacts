// Configure logging, growl notification, etc.
(function() {
    "use strict";
    
    angular.module("ngContacts").config(appConfig);

    appConfig.$inject = ["logEnhancerProvider", "growlProvider"];
    
    function appConfig(logEnhancerProvider, growlProvider) {
        // Customize datetime format for logging
        logEnhancerProvider.prefixPattern = "%s::[%s] - ";
        logEnhancerProvider.datetimePattern = "YYYY-MM-DD HH:mm:ss,SSS";
        
        // Growl config
        growlProvider.onlyUniqueMessages(false); // Set to false, to always display all messages regardless if they are already displayed or not
        growlProvider.globalTimeToLive(2500);
        growlProvider.globalDisableCountDown(true);
        growlProvider.globalPosition("top-center");
        growlProvider.globalDisableIcons(true);
    }

})();
