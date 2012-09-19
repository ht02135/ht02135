
// namespace - ooNS
var ooNS = {};

// --------------------------------------- HUMAN ---------------------------------------------

// ooNS.Human constructor
ooNS.Human = function(aName, aAddress) {
	this.name = aName;
	this.address = aAddress;
};

// getName method
ooNS.Human.prototype.getName = function () {
	return this.name;
};

ooNS.Human.prototype.setName = function (aName) {
	this.name = aName;
};

// ------------------------------------------ SUPER HUMAN ------------------------------------------

// ooNS.SuperHuman constructor
ooNS.SuperHuman = function(aName, aAddress) {
	// call constructor of ooNS.Human
	ooNS.Human.call(this, aName, aAddress);
	this.isSuper = true;
};	

// we set ooNS.SuperHuman.prototype to an obj of our creation.
// we override prototype obj provided by javascript and discarded the constructor property we are given
ooNS.SuperHuman.prototype = new ooNS.Human("","");	// inherit from ooNS.Human

//correct the constructor pointer because it points to Human
ooNS.SuperHuman.prototype.constructor = ooNS.SuperHuman;


ooNS.SuperHuman.prototype.change = function () {
	this.name = "SUPER " + this.name;
};

ooNS.SuperHuman.prototype.isSuperHuman = function () {
	return this.isSuper;
};

// ------------------------------------------ HUMAN FACTORY ----------------------------------------

ooNS.HumanFactory = function HumanFactory() {};

ooNS.HumanFactory.createHuman = function(name, address) {
	return new ooNS.Human(name, address);
};

ooNS.HumanFactory.createSuperHuman = function(name, address) {
	return new ooNS.SuperHuman(name, address);
};

