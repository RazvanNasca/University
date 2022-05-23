#pragma once
#include <vector>
#include <algorithm>
#include <random>    // std::default_random_engine
#include <chrono>    // std::chrono::system_clock

#include "pet.h"
using std::vector;
/*
  Clasa care implementeaza ideea de cos
*/
class CosPet {
	vector<Pet> inCos;
public:
	CosPet() = default;

	void adauga(const Pet& p) {
		inCos.push_back(p);
	}
	void goleste() {
		inCos.clear();
	}
	/*
	Umple cosul aleator
	*/
	void umple(size_t cate, vector<Pet> all) {
		std::shuffle(all.begin(), all.end(), std::default_random_engine(std::random_device{}())); //amesteca vectorul v
		while (inCos.size() < cate && all.size()>0) {
			inCos.push_back(all.back());
			all.pop_back();
		}
	}

	const vector<Pet>& lista() const {
		return inCos;
	}
};