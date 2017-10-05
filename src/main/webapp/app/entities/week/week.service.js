(function() {
    'use strict';
    angular
        .module('simpleTimeTrackApp')
        .factory('Week', Week);

    Week.$inject = ['$resource'];

    function Week ($resource) {
        var resourceUrl =  'api/weeks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
