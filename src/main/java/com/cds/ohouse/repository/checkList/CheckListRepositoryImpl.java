package com.cds.ohouse.repository.checkList;

import static com.cds.ohouse.domain.QCheckList.*;
import static java.util.Objects.nonNull;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CheckListRepositoryImpl implements CheckListCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CheckList> search(TradeState tradeState, CheckListSortType checkListSortType, Pageable pageable) {
        return queryFactory
            .selectFrom(checkList)
            .where(
                whereCondition(tradeState)
            )
            .orderBy(sortCondition(checkListSortType))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression whereCondition(TradeState tradeState) {
        return nonNull(tradeState) ? checkList.tag.state.eq(tradeState) : null;
    }

    private OrderSpecifier sortCondition(CheckListSortType checkListSortType) {
        return checkListSortType.equals(CheckListSortType.GRADE) ? checkList.grade.desc(): checkList.good.desc();
    }
}
