var likeApp = angular.module('likeApp', []);

likeApp.controller('likeController', function ($scope, $http) {
  $http.get('/likes').success(function(data){
    $scope.likes = data;  
  });
});