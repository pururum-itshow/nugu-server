package com.nugu.greenery.NuguServer.entity.repository;
import com.nugu.greenery.NuguServer.entity.Humidity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HumidityRepository extends JpaRepository<Humidity, Long>, HumidityRepositoryExtension {

}
