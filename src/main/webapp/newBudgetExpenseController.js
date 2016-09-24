angular
  .module("budgetsApp")
  .controller("newBudgetExpenseController", function($scope, $http, $routeParams, $location) {

    $http
      .get("/budgets/budget/" + $routeParams.budgetId)
      .then(function(response) {
        $scope.budget = response.data;
      });

    $scope.submitExpense = function(amount) {

      request = {
        budgetId: $scope.budget.id,
        amount: $scope.amount
      };

      $http
        .post("/budgets/newExpense", request)
        .then(function(response) {

           alert(response.data.value);
           $location.path("/");
        });

    }

  });