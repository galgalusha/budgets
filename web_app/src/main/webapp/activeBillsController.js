angular
  .module("budgetsApp")
  .controller("activeBillsController", function($scope, $http, $location) {

    $http
      .get("/budgets/rest/activeBills")
      .then(function(response) {
        $scope.activeBills = response.data;
      });

    $scope.calcUsed = function(activeBill) {
      return Math.round((100 * activeBill.billAmount) / activeBill.budgetAmount);
    }

    $scope.onClickNewExpense = function(activeBill) {
      $location.path("/newExpense/budget/" + activeBill.budgetId);
    }

    $scope.onClickNewBudget = function() {
      $location.path("/newBudget");
    }

  });
