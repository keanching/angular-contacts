// Configure the routes.
(function() {
    "use strict";
    
    angular.module("ngContacts").config(routeConfig);

    routeConfig.$inject = ["$stateProvider", "$stickyStateProvider", "$urlRouterProvider"];
    
    function routeConfig($stateProvider, $stickyStateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/search/index"); // For any unmatched url
        $urlRouterProvider.when("/search", "/search/index");
        
        // Set up the states
        $stateProvider
            .state("about", {
                url: "/about",
                templateUrl: "assets/js/about/about.html"
            })
            .state("contactus", {
                url: "/contactus",
                templateUrl: "assets/js/contact/contact.html",
                controller: "contactController as contactCtrl"
            })
            .state("search", {
                // This state will prepend '/search' onto the urls of all its children
                url: "/search",
                // Example of loading a template from a file. This is also a top level state, so this template file will be loaded
                // and then inserted into the ui-view within index.html.
                // The template must contain a <ui-view /> directive, which will be populated with the child state's template.
                templateUrl: "assets/js/search/search.html",
                controller: "searchController as searchCtrl",
                //abstract: true // Not sure if this should be abstract or not?
            })
            // Define the sticky state
            // Mark a state with sticky: true.
            // Optionally, also mark the state with deepStateRedirect: true to get the tab to redirect.
            .state("search.index", {
                url: "/index",
                sticky: true,
                //dsr: true,
                views: {
                    // For sticky states:
                    //     Declare a named view on the state which targets a named ui-view in the parent state's template.
                    //         The sticky state view must target its own unique named ui-view.
                    //         Example: views: { bar: { /* "bar" named-view definition */ } }
                    //     Add the named ui-view to the parent state's template.
                    //         Example: <div ui-view="bar" />
                    //         This named ui-view must not be targeted by any other states. This reserved <ui-view="bar"/> tag is where the DOM for your sticky state is retained.
                    //         Optionally, hide the named ui-view when the state is not activated: <div ui-view="bar" ng-show="$state.includes("foo.bar") />
                    //
                    // This also demonstrates having multiple ui-view's per template.
                    // Think of this as the template that holds the layout.
                    "search-index": {
                        templateUrl: "assets/js/search/index/index.html"
                    },
                    // Absolutely targets the "quick-search" view (named view) in the "search.index" state
                    // <div ui-view="quick-search" /> within "assets/js/search/index/index.html"
                    "quick-search@search.index": {
                        templateUrl: "assets/js/search/index/quick.search.html",
                        controller: "quickSearchController as quickSearchCtrl"
                    },
                    // Absolutely targets the "search-results" view (named view) in the "search.index" state
                    // <div ui-view="search-results" /> within "assets/js/search/index/index.html"
                    "search-results@search.index": {
                        templateUrl: "assets/js/search/index/search.results.html",
                        controller: "searchResultsController as searchResultsCtrl"
                    }
                }
            })
            .state("search.create", {
                url: "/create",
                templateUrl: "assets/js/search/create/create.html",
                controller: "createController as createCtrl"
            })
            .state("search.favorites", {
                url: "/favorites",
                templateUrl: "assets/js/search/favorites/favorites.html",
                controller: "favoritesController as favCtrl"
//                resolve: {
//                    favoritesPromise: favoritesServiceProvider.$get().getFavorites
//                }
            })
            .state("search.details", {
                url: "/details/:contactId",
                templateUrl: "assets/js/search/details/details.html",
                controller: "detailsController as detailsCtrl"
            });
        
        $stickyStateProvider.enableDebug(false);
    }

})();
