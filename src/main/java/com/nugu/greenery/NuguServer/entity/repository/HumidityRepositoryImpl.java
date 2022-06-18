package com.nugu.greenery.NuguServer.entity.repository;

import com.nugu.greenery.NuguServer.entity.Humidity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.nugu.greenery.NuguServer.entity.QHumidity.humidity;

public class HumidityRepositoryImpl extends QuerydslRepositorySupport implements HumidityRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public HumidityRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Humidity.class);

        this.queryFactory = queryFactory;
    }


    @Override
    public Humidity findLastestHumidity() {
        return queryFactory
                .select(Projections.fields(
                        Humidity.class,
                        humidity.value,
                        humidity.datetime
                ))
                .from(humidity)
                .orderBy(humidity.datetime.desc())
                .fetchFirst();
    }
}
