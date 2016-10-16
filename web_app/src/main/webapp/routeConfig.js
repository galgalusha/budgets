var app = angular.module('budgetsApp', ['auth0', 'angular-storage', 'angular-jwt', 'ngRoute']);

app.config(function ($routeProvider, authProvider, $httpProvider, $locationProvider, jwtInterceptorProvider) {

  $routeProvider

    .when("/",
      {
        templateUrl: "activeBills.html",
        controller: "activeBillsController"
      })

    .when("/login",
      {
        templateUrl: "login.html",
        controller: "loginController"
      })

    .when("/newExpense/budget/:budgetId",
      {
        templateUrl: "newBudgetExpense.html",
        controller: "newBudgetExpenseController"
      })
  ;


  authProvider.init({
        clientID: 'ztfqtH60zLiRVi6ylwnTJLEHZmEE4YM1',
        domain: 'galko.eu.auth0.com',
        loginUrl: '/login'
  });

  //Called when login is successful
  authProvider.on('loginSuccess', function($location, profilePromise, idToken, store) {
    console.log("Login Success");
    profilePromise.then(function(profile) {
      store.set('profile', profile);
      store.set('token', idToken);
      $location.path('/');
    });
  });

  authProvider.on('loginFailure', function() {
    alert("Error");
  });

  authProvider.on('authenticated', function($location) {
    console.log("Authenticated");
  });

  jwtInterceptorProvider.tokenGetter = function(store) {
    return store.get('token');
  };

  // Add a simple interceptor that will fetch all requests and add the jwt token to its authorization header.
  // NOTE: in case you are calling APIs which expect a token signed with a different secret, you might
  // want to check the delegation-token example
  $httpProvider.interceptors.push('jwtInterceptor');
});

app.run(['auth', function(auth) {
  // This hooks all auth events to check everything as soon as the app starts
  auth.hookEvents();
}]);

app.run(function($rootScope, auth, store, jwtHelper, $location) {
  $rootScope.$on('$locationChangeStart', function() {

    var token = store.get('token');
    if (token && !jwtHelper.isTokenExpired(token)) {
        if (!auth.isAuthenticated) {
          //Re-authenticate user if token is valid
          auth.authenticate(store.get('profile'), token);
        }
      } else {
        // Either show the login page or use the refresh token to get a new idToken
        $location.path('/login');
      }
  });
});

app.controller( 'AppCtrl', function AppCtrl ( $scope, $location ) {
  $scope.$on('$routeChangeSuccess', function(e, nextRoute){
    if ( nextRoute.$$route && angular.isDefined( nextRoute.$$route.pageTitle ) ) {
      $scope.pageTitle = nextRoute.$$route.pageTitle + ' | Auth0 Sample' ;
    }
  });
});
