(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('UserManagementRaportController', UserManagementRaportController);

    UserManagementRaportController.$inject = ['$stateParams', 'User'];

    function UserManagementRaportController($stateParams, User) {
        var vm = this;

        vm.load = load;
        vm.user = {};

        vm.load($stateParams.login);

        function load(login) {
            User.get({login: login}, function(result) {
                vm.user = result;
            });
        }
    }
})();
