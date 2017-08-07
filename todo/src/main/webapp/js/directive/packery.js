myApp.directive('testpackery1', ['$rootScope', '$timeout',
	  function($rootScope, $timeout) {
	    return {
	      restrict: 'A',
	      link: function(scope, element, attrs) {
	        // console.log("link called on", element[0]);
	        if ($rootScope.packery === undefined || $rootScope.packery === null) {
	          scope.element = element;
	          $rootScope.packery = new Packery(element[0].parentElement, {
	            isResizeBound: true,
	            // rowHeight: 230,
	            // columnWidth: 230,
	            itemSelector: '.item'
	          });
	          $rootScope.packery.bindResize();
	          var draggable1 = new Draggabilly(element[0]);
	          $rootScope.packery.bindDraggabillyEvents(draggable1);

	          draggable1.on('dragEnd', function(instance, event, pointer) {
	            $timeout(function() {
	              $rootScope.packery.layout();
	              // $rootScope.packery.reloadItems();
	            }, 200);
	          });


	          // var orderItems = function() {
	          //   var itemElems = $rootScope.packery.getItemElements();
	          //   console.log(itemElems);
	          //   $(itemElems).each(function(i, itemElem) {
	          //     $(itemElem).text(i + 1);
	          //   });
	          // };

	          // $rootScope.packery.on('layoutComplete', orderItems);
	          // $rootScope.packery.on('dragItemPositioned', orderItems);


	        } else {
	          // console.log("else", element[0]);
	          var draggable2 = new Draggabilly(element[0]);
	          $rootScope.packery.bindDraggabillyEvents(draggable2);


	          draggable2.on('dragEnd', function(instance, event, pointer) {
	            $timeout(function() {
	              $rootScope.packery.layout();
	              /*$rootScope.packery = null;*/
	            }, 200);
	          });

	        }
	        $timeout(function() {
	          $rootScope.packery.reloadItems();
	          $rootScope.packery.layout();
	        }, 100);
	      }
	    };

	  }
	])