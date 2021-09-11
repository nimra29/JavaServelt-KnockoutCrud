define([ "binder" ], function(_binder) {
	var binder = _binder;
	function structure(data){
		this.structureId=ko.observable(data.structureId)
		this.lifecyclePhases=ko.observable(data.lifecyclePhases)
		this.createdBy=ko.observable(data.createdBy)
		this.creationDate=ko.observable(data.creationDate)
	}

	function testVM() {
		var self = this;
		
		
		self.itemstructure=ko.observableArray([]);
		self.structureId=ko.observable()
		self.lifecyclePhases=ko.observable()
		self.createdBy=ko.observable()
		self.creationDate=ko.observable();
		
		var itemToDelete = new Object();
		
		
		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);
		
		self.EditUser = function(data){
			
			$(".bs-example-modal-smss").modal('show')
			self.structureId(data.structureId());
		  	self.lifecyclePhases(data.lifecyclePhases());
			self.createdBy(data.createdBy());
 		 }
		self.handleEdit=function(){
		
			var req = new Object();
			req.structureId=self.structureId();
			req.lifecyclePhases=self.lifecyclePhases();
			req.createdBy=self.createdBy();
			req.id=parseInt(localStorage.getItem('id'));
			req = JSON.stringify(req);
			console.log(req);

			System.sendPutRequest("itemstructureapi", req, function(response) {
				console.log(response)
				self.itemstructure.removeAll();
				for(var i=0; i<response.length; i++){
					self.itemstructure.push(new structure({structureId:response[i].structureId,lifecyclePhases:response[i].lifecyclePhases,createdBy:response[i].createdBy,creationDate:response[i].creationDate}))
				}
				
			});
			$(".bs-example-modal-smss").modal('hide');
			
		}
		
		self.onLoad = function() {
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.action="get";
			req = JSON.stringify(req);
			console.log(req);
			
			System.sendPostRequest("itemstructureapi", req, function(response) {
				console.log(response);
				for(var i=0; i<response.length; i++){
					self.itemstructure.push(new structure({structureId:response[i].structureId,lifecyclePhases:response[i].lifecyclePhases,createdBy:response[i].createdBy,creationDate:response[i].creationDate}))
				}
				
				
			});
		};
		
		self.AddUser = function(user){
			self.lifecyclePhases("")
			self.createdBy("")
		  	$(".bs-example-modal-lg").modal('show')
		  
 		 }
		self.handleAdd=function(){
			
			
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.lifecyclePhases=self.lifecyclePhases();
			req.createdBy=self.createdBy();
			req.action="post";
			req = JSON.stringify(req);
			
			console.log(req);

			System.sendPostRequest("itemstructureapi", req, function(response) {
				console.log(response);
				
					
					self.itemstructure.push(new structure({structureId:response.structureId,lifecyclePhases:response.lifecyclePhases,createdBy:response.createdBy,creationDate:response.creationDate}))
				
			});
			$(".bs-example-modal-lg").modal('hide');
		}
		
		self.DeleteUser=function(deleteitem){
			
			$(".bs-example-modal-del").modal('show')
			console.log(deleteitem.structureId());
			itemToDelete=deleteitem;
			
		}
		self.handleDelete=function(){
			var req = new Object();
			req.structureId=itemToDelete.structureId();
			req = JSON.stringify(req);
			console.log(req);

			System.sendDeleteRequest("itemstructureapi", req, function(response) {
				console.log(response);
				if(response===true){
					
					self.itemstructure.remove(itemToDelete)
				
					
				}
			
			});
			$(".bs-example-modal-del").modal('hide');
		}
		self.navigateToItemStructure=function(){
			window.open("http://localhost:8080/TemplateProject/#itemstructure","_self")


		}
		self.closeWindow = function() {
			window.close();
		}
	}

	return {
		getInstance : function() {
			return new testVM();
		}
	};
});
