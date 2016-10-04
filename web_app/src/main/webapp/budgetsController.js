angular
  .module("budgetsApp")
  .controller("budgetsController", function($scope, $http, $location) {

    $http
      .get("/budgets/rest/budgets")
      .then(function(response) {
        $scope.budgets = response.data;
      });

    $scope.calcUsed = function(budget) {
      return Math.round((100 * budget.used) / budget.allocation);
    }

    $scope.onClick = function(budget) {
      $location.path("/newExpense/budget/" + budget.id);
    }

  });
