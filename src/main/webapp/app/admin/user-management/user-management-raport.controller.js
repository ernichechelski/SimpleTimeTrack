(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('UserManagementRaportController', UserManagementRaportController);

    UserManagementRaportController.$inject = ['$stateParams', 'User'];

    function UserManagementRaportController($stateParams, User,calendarConfig,moment, alert) {
        var vm = this;


        Array.prototype.sum = function (prop) {
            var total = 0
            for ( var i = 0, _len = this.length; i < _len; i++ ) {
                total += this[i][prop]
            }
            return total
        }



        vm.load = load;
        vm.user = {};


        //Calendar config:
        vm.calendarView = 'month';
        vm.viewDate = new Date()
        //Example values:
        vm.exampleStartDate = new Date();
        vm.exampleStartDate.setHours(12,0);

        //Static data, needs endpoint with provided model.
        vm.events = [
             {
               startsAt: vm.exampleStartDate, // A javascript date object for when the event starts
               hours:2,
             }
        ];

        vm.load($stateParams.login);

        function load(login) {
            User.get({login: login}, function(result) {
                vm.user = result;
            });
        }
    }
})();
