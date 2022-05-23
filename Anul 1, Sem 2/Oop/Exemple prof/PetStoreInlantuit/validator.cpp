#include "validator.h"
#include <assert.h>
#include <sstream>
using std::stringstream;

void PetValidator::validate(const Pet& p) {
	vector<string> msgs;
	if (p.getPrice() < 0) msgs.push_back("Pret negativ!!!");
	if (p.getType().size() == 0) msgs.push_back("Tip vid!!!");
	if (p.getSpecies().size() == 0) msgs.push_back("Specie vid!!!");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& msg : ex.msgs) {
		out << msg<<" ";
	}
	return out;
}

void testValidator() {
	Pet p{ "","",-1 };
	PetValidator val;
	try {
		val.validate(p);
		assert(false);
	}
	catch (ValidateException ex) {
		stringstream ss;
		ss << ex;
		auto errorMsg = ss.str();
		assert(errorMsg.find("vid") >= 0);
		assert(errorMsg.find("negativ") >= 0);
	}
}