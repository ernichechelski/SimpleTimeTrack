(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$cookies','$cookieStore'];

    function HomeController ($scope, Principal, LoginService, $state, $cookies, $cookieStore) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
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
                $cookieStore.put("user", vm.account.login);
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
