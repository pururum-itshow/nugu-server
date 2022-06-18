package com.nugu.greenery.NuguServer.entity.repository;

import com.nugu.greenery.NuguServer.entity.Humidity;

public interface HumidityRepositoryExtension{
    Humidity findLastestHumidity();
}
