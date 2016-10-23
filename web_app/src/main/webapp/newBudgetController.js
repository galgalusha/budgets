angular
  .module("budgetsApp")
  .controller("newBudgetController", function($scope, $http, $location) {

    $scope.submitBudget = function(name, amount) {

      request = {
        name:   $scope.name,
        amount: $scope.amount
      };

      $http
        .post("/budgets/rest/newBudget", request)
        .then(function(response) {

           alert(response.data.value);
           $location.path("/");
        });

    }

  });
