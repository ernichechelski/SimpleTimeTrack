(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$cookies','$cookieStore'];

    function HomeController ($scope, Principal, LoginService, $state, $cookies, $cookieStore) {
        var vm = this;


        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
        
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
