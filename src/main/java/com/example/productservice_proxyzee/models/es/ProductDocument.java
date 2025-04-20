package com.example.productservice_proxyzee.models.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "products")
@Getter @Setter
public class ProductDocument {
    @Id
    private String id;

    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String title;

    private String description;

    private double price;

    private String imageUrl;

    private String categoryName;
}

