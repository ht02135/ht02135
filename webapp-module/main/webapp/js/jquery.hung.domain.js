
// namespace - domainNS
var domainNS = {};

// ----------------------------------------- JSON DOMAIN ---------------------------------------------------
//domainNS.Domain constructor
domainNS.JSONDomain = function(name, parentDomainName) {
	this.name = name;
	this.parentDomainName = parentDomainName;
};	

// ------------------------------------------ DOMAIN --------------------------------------------------------
// domainNS.Domain constructor
domainNS.Domain = function(domainsURL, domainList, name, parentDomainName) {
	console.log('Domain.constructor: called');
	
	this.domainsURL = domainsURL;
	this.domainList = domainList;				// id of domainList dom element
	this.name = name;							// id of name dom element
	this.parentDomainName = parentDomainName;	// id of parentDomainName dom element
};	

//-------------------------------------------- DOMAIN METHODS - VIEW HELPER ---------------------------------
// renderDomainList(data)
domainNS.Domain.prototype.renderDomainList = function(data) {
	console.log('Domain.prototype.renderDomainList: data='+data);
	
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#'+this.domainList+' li').remove();
	$.each(list, function(index, domain) {
		$('#'+this.domainList).append('<li><a href="#" name="'+domain.name+'">'+domain.name+'</a></li>');
	});
};

// renderDomainDetail(data)
domainNS.Domain.prototype.renderDomainDetail = function(data) {
	console.log('Domain.prototype.renderDomainDetail: data='+data);
	
	$('#'+this.name).val(data.name);
	$('#'+this.parentDomainName).val(data.parentDomainName);
};

// -------------------------------------------- DOMAIN METHODS - HELPER ------------------------------------------
// toJSONString(name, parentDomainName) is helper method to serialize all the form fields into a JSON string
domainNS.Domain.prototype.toJSONString = function(name, parentDomainName) {
	console.log('Domain.prototype.toJSONString: name='+name+',parentDomainName='+parentDomainName);
	
	var jsonDomain = new domainNS.JSONDomain(name, parentDomainName);	// js object
	var jsonDomainString = JSON.stringify(jsonDomain);					// convert js object to JSON string
	console.log('Domain.prototype.toJSONString: jsonDomainString='+jsonDomainString);
	
	return jsonDomainString;
};

// --------------------------------------------- DOMAIN METHODS -------------------------------------------------

// findAll method (shared across Domain instance)
domainNS.Domain.prototype.findAll = function () {
	console.log('Domain.prototype.findAll: called');
	
    $.ajaxSetup ({ accepts: { json: "application/json" } });
	$.ajax({
		type: 'GET',
		url: this.domainsURL,
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			console.log('Domain.prototype.findAll callback: data='+data+',textStatus='+textStatus);
			this.renderDomainList(data);	// data is list of domain in json
		},
		error: function(jqXHR, textStatus, errorThrown) {	
			console.log('Domain.prototype.findAll callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);
			alert('Domain.prototype.findAll callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);			
		}
	});
};

// findByName(name) method (shared across Domain instance)
domainNS.Domain.prototype.findByName = function (name) {
	console.log('Domain.prototype.findByName: name='+name);
	
	$.ajaxSetup ({ accepts: { json: "application/json" } });
	$.ajax({
		type: 'GET',
		url: this.domainsURL + '/' + name,
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			console.log('Domain.prototype.findByName callback: data='+data+',textStatus='+textStatus);
			this.renderDomainList(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('Domain.prototype.findByName callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);
			alert('Domain.prototype.findByName callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);		
		}
	});
};

// findById(id) method (shared across Domain instance)
domainNS.Domain.prototype.findById = function (id) {
	console.log('Domain.prototype.findById: id='+id);
	
	$.ajaxSetup ({ accepts: { json: "application/json" } });
	$.ajax({
		type: 'GET',
		url: this.domainsURL + '/' + id,
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			console.log('Domain.prototype.findById callback: data='+data+',textStatus='+textStatus);
			this.renderDomainDetail(data);	// data is domain in json
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('Domain.prototype.findById callback: ERROR, jqXHR='+jqXHR+',textStatus='+textStatus);
			alert('Domain.prototype.findById callback: ERROR, jqXHR='+jqXHR+',textStatus='+textStatus);
		}
	});
};

//search(searchKey) method (shared across Domain instance)
domainNS.Domain.prototype.search = function (searchKey) {
	console.log('Domain.prototype.search: searchKey='+searchKey);
	
	if (searchKey == '') 
		this.findAll();
	else
		this.findByName(searchKey);
};

// addDomain(name, parentDomainName) method
domainNS.Domain.prototype.addDomain = function (name, parentDomainName) {
	console.log('Domain.prototype.addDomain: name='+name+',parentDomainName='+parentDomainName);
	
	$.ajaxSetup ({ accepts: { json: "application/json" } });
	$.ajax({
		type: 'POST',
		url: this.domainsURL,
		dataType: 'json',										// Expected data format from server
		contentType: 'application/json; charset=UTF-8',			// content type sent to server
		data: this.toJSONString(name, parentDomainName),				// Data sent to server
		success: function(data, textStatus, jqXHR){
			console.log('Domain.prototype.addDomain callback: data='+data+',textStatus='+textStatus);
			alert('Domain created');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('Domain.prototype.addDomain callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);
			alert('Domain.prototype.addDomain callback: ERROR, jqXHR='+jqXHR+',jqXHR.responseText='+jqXHR.responseText+',textStatus='+textStatus+ ',errorThrown='+errorThrown);
		}
	});
};

// updateDomain(name, parentDomainName) method
domainNS.Domain.prototype.updateDomain = function (name, parentDomainName) {
	console.log('Domain.prototype.updateDomain: name='+name+',parentDomainName='+parentDomainName);
	
	$.ajaxSetup ({ accepts: { json: "application/json" } });
	$.ajax({
		type: 'POST',
		url: this.domainsURL + '/' + name,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		data: this.toJSONString(name, parentDomainName),
		success: function(data, textStatus, jqXHR){
			console.log('Domain.prototype.updateDomain callback: data='+data+',textStatus='+textStatus);
			alert('Domain updated');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('Domain.prototype.updateDomain callback: ERROR, jqXHR='+jqXHR+',textStatus='+textStatus);
			alert('Domain.prototype.updateDomain callback: ERROR, jqXHR='+jqXHR+',textStatus='+textStatus);
		}
	});
};

//clearDomain() method
domainNS.Domain.prototype.clearDomain = function () {
	console.log('Domain.prototype.clearDomain: called');
	
	this.renderDomainDetail({});	// display empty form
};

//------------------------------------------ HUMAN FACTORY ----------------------------------------

domainNS.DomainFactory = function DomainFactory() {};

// createDomain(domainsURL, domainList, name, parentDomainName) (static method)
domainNS.DomainFactory.createDomain = function(domainsURL, domainList, name, parentDomainName) {
	console.log('DomainFactory.createDomain: called');
	return new domainNS.Domain(domainsURL, domainList, name, parentDomainName);
};

//--------------------------------------------- REGISTER LISTENERS ---------------------------------------------------------------

jQuery(document).ready(function($) {
	console.log('ready: called');
	
	// domainsURL=http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains
	// domainsURL=http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest/domains
	
	var domain = domainNS.DomainFactory.createDomain('http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains',
		'domainList', 'name', 'parentDomainName');
	
	// retrive domain list when application starts
	domain.findAll();
	
	$('#btnSearch').click(function() {
		domain.search($('#searchKey').val());
		return false;
	});

	$('#searchKey').keypress(function(e){
		if(e.which == 13) {
			domain.search($('#searchKey').val());
			e.preventDefault();
			return false;
	    }
	});

	$('#btnAdd').click(function() {
		domain.addDomain($('#name').val(), $('#parentDomainName').val());
		return false;
	});
	
	$('#btnModify').click(function() {
		domain.updateDomain($('#name').val(), $('#parentDomainName').val());
		return false;
	});
	
	$('#btnClear').click(function() {
		domain.clearDomain();
		return false;
	});

	$('#domainList a').live('click', function() {
		domain.findById($(this).data('name'));
	});
});