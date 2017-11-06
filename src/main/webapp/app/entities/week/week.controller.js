(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('WeekController', WeekController);

    WeekController.$inject = ['Principal','Week', '$cookies','$cookieStore'];

    function WeekController(Principal,Week, $cookies, $cookieStore) {

        var vm = this;
        vm.weeks = [];
        vm.isAdmin = false;

        checkIfAdmin();

        function checkIfAdmin() {
            Principal.identity().then(function(account) {
                if ( $.inArray('ROLE_ADMIN', $(account.authorities)) > -1 ){
                    vm.isAdmin = true;
                }
            });
        }


        loadAll();

        function loadAll() {
            Week.query(function(result) {
                vm.weeks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
