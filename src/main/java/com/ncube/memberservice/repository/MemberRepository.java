package com.ncube.memberservice.repository;

import com.ncube.memberservice.domain.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {

    Member findBy_id(ObjectId id);

    void removeAllByActiveFalse();
}
