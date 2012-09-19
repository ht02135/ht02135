
// --------------------------------------- HUMAN ---------------------------------------------

// Human constructor
function Human(name, address) {
	this.name = name;
	this.address = address;
}

// getName method
Human.prototype.getName = function () {
	return this.name;
}

Human.prototype.setName = function (name) {
	this.name = name;
}

// ------------------------------------------ SUPER HUMAN ------------------------------------------

// SuperHuman constructor
function SuperHuman(name, address) {
	// call constructor of Human
	Human.call(this, name, address);
	this.isSuper = true;
}	

// we set SuperHuman.prototype to an obj of our creation.
// we override prototype obj provided by javascript and discarded the constructor property we are given
SuperHuman.prototype = new Human("","");	// inherit from Human

//correct the constructor pointer because it points to Human
SuperHuman.prototype.constructor = SuperHuman;


SuperHuman.prototype.change = function () {
	this.name = "SUPER " + this.name;
}

SuperHuman.prototype.isSuperHuman = function () {
	return this.isSuper;
}

//------------------------------------------ HUMAN FACTORY ----------------------------------------

function HumanFactory() {}	

// non-prototype considered static
HumanFactory.createHuman = function(name, address) {
	return new Human(name, address);
}

HumanFactory.createSuperHuman = function(name, address) {
	return new SuperHuman(name, address);
}
