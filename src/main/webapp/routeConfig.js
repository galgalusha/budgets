angular
  .module("budgetsApp", ["ngRoute"])
  .config(function($routeProvider) {

    $routeProvider

      .when("/",
        {
          templateUrl: "budgets.html",
          controller: "budgetsController"
        })

      .when("/newExpense/budget/:budgetId",
        {
          templateUrl: "newBudgetExpense.html",
          controller: "newBudgetExpenseController"
        })
    ;

  });
