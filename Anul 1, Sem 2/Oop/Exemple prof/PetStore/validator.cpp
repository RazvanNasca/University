#include "validator.h"
#include <assert.h>
#include <sstream>

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
	PetValidator v;
	Pet p{ "","",-1 };
	try {
		v.validate(p);
	}
	catch (const ValidateException & ex) {
		std::stringstream sout;
		sout << ex;
		auto mesaj = sout.str();
		assert(mesaj.find("negativ") >= 0);
		assert(mesaj.find("vid") >= 0);
	}

}