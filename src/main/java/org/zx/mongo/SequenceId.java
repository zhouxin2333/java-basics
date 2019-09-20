package org.zx.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "sequence")
public class SequenceId {

    @Id
    private String id;

    @Field("seq_id")
    private long seqId;
 
    @Field("coll_name")
    private String collName;

}