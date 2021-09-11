define([ "binder" ], function(_binder) {
	var binder = _binder;
	function structure(data){
		this.registryId=ko.observable(data.registryId)
		this.manufacturPart=ko.observable(data.manufacturPart)
		this.manufacturer=ko.observable(data.manufacturer)
		
	}

	function testVM() {
		var self = this;
		
		
		self.itemstructure=ko.observableArray([]);
		self.registryId=ko.observable()
		self.manufacturPart=ko.observable()
		self.manufacturer=ko.observable()
		
		var itemToDelete = new Object();
		
		
		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);
		
		self.EditUser = function(data){
			
			$(".bs-example-modal-smss").modal('show')
			self.registryId(data.registryId());
		  	self.manufacturPart(data.manufacturPart());
			self.manufacturer(data.manufacturer());
 		 }
		self.handleEdit=function(){
			
			var req = new Object();
			req.registryId=self.registryId();
			req.manufacturPart=self.manufacturPart();
			req.manufacturer=self.manufacturer();
			req.id=parseInt(localStorage.getItem('id'));
			req = JSON.stringify(req);
			console.log(req);

			System.sendPutRequest("itemamlapi", req, function(response) {
				self.itemstructure.removeAll();
				for(var i=0; i<response.length; i++){
					self.itemstructure.push(new structure({registryId:response[i].registryId,manufacturPart:response[i].manufacturerPart,manufacturer:response[i].manufacturer}))
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

			System.sendPostRequest("itemamlapi?itemNumber=43", req, function(response) {
				console.log(response);
				for(var i=0; i<response.length; i++){
					self.itemstructure.push(new structure({registryId:response[i].registryId,manufacturPart:response[i].manufacturerPart,manufacturer:response[i].manufacturer}))
				}
				
				
			});
		};
		
		self.AddUser = function(user){
			self.manufacturPart("")
			self.manufacturer("")
		  	$(".bs-example-modal-lg").modal('show')
		  
 		 }
		self.handleAdd=function(){
			
			
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.manufacturPart=self.manufacturPart();
			req.manufacturer=self.manufacturer();
			req.action="post";
			req = JSON.stringify(req);
			console.log(req);

			System.sendPostRequest("itemamlapi", req, function(response) {
				console.log(response);
				if(response!==0){
					console.log("here i am");
					self.itemstructure.push(new structure({registryId:response,manufacturPart:self.manufacturPart(),manufacturer:self.manufacturer()}))
				}
			});
			$(".bs-example-modal-lg").modal('hide');
		}
		
		self.DeleteUser=function(deleteitem){
			
			$(".bs-example-modal-del").modal('show')
		
			itemToDelete=deleteitem;
			
		}
		self.handleDelete=function(){
			var req = new Object();
			req.registryId=itemToDelete.registryId();
			req = JSON.stringify(req);
			console.log(req);

			System.sendDeleteRequest("itemamlapi", req, function(response) {
				console.log(response);
				if(response===true){
					console.log("here i am");
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
