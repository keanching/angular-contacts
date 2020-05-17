(function() {
    "use strict";

//    var WEB_CTX_URL = "/angular-contacts";
    var WEB_CTX_URL = "";
    var IMG_URL = WEB_CTX_URL + "/assets/img";
    var REST_API_URL = WEB_CTX_URL + "/api";
    var DATE_FORMAT_LONG = "MMM Do YYYY";
    
    angular.module("ngContacts").constant("CONSTANTS", {
        WEB_CTX_URL: WEB_CTX_URL,
        IMG_URL: IMG_URL,
        REST_API_URL: REST_API_URL,
        DATE_FORMAT_LONG: DATE_FORMAT_LONG
    });
    
})();
