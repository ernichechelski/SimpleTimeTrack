(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('WeekController', WeekController);

    WeekController.$inject = ['Week', '$cookies','$cookieStore'];

    function WeekController(Week, $cookies, $cookieStore) {

        var vm = this;
        vm.weeks = [];
        vm.account = $cookieStore.get("user");
        
      
        
        loadAll();
        
        function loadAll() {
            Week.query(function(result) {
                vm.weeks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
