package com.example.exercises;

import java.util.*;

import com.example.dao.CityDao;
import com.example.dao.CountryDao;
import com.example.dao.InMemoryWorldDao;
import com.example.domain.City;
import com.example.domain.Country;

/**
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Exercise4 {
    private static final CountryDao countryDao = InMemoryWorldDao.getInstance();
    private static final CityDao cityDao = InMemoryWorldDao.getInstance();

    public static void main(String[] args) {
        // Find the highest populated capital city
        City highestPopulatedCapital = getHighestPopulatedCapitalBasic();
        System.out.printf("Highest populated capital with basic algorithm %s\n", highestPopulatedCapital);
        Optional<City> maybeHighestPopulatedCapital = getHighestPopulatedCapitalStream();
        maybeHighestPopulatedCapital.ifPresent(capital -> System.out.printf("Highest populated capital with basic algorithm %s\n", capital));
    }

    private static City getHighestPopulatedCapitalBasic() {
        List<City> capitals = new ArrayList<>();
        List<Integer> capitalIds = new ArrayList<>();
        for (Country country : countryDao.findAllCountries()) {
            int capitalId = country.getCapital();
            if (capitalId != -1) {
                capitalIds.add(capitalId);
            }
        }
        for (Integer capitalId : capitalIds) {
            capitals.add(cityDao.findCityById(capitalId));
        }
        City highestPopulatedCapital = null;
        for (City capital : capitals) {
            if ((highestPopulatedCapital == null) ||
                    (highestPopulatedCapital.getPopulation() < capital.getPopulation())) {
                highestPopulatedCapital = capital;
            }
        }
        return highestPopulatedCapital;
    }

    private static Optional<City> getHighestPopulatedCapitalStream() {
        return countryDao.findAllCountries()
                .stream()
                .map((Country country) -> country.getCapital())
                .filter((Integer capitalId) -> capitalId >= 0)
                .map((Integer capitalId) -> cityDao.findCityById(capitalId))
                .reduce((City c1, City c2) -> c1.getPopulation() > c2.getPopulation() ? c1 : c2);

    }

}
