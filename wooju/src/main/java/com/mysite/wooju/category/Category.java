package com.mysite.wooju.category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mysite.wooju.sale.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Category {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
    private Integer id;
	
	private Long idParent;

	private String name;
	
	private String description;
	
	@CreationTimestamp
	private LocalDateTime createTimestamp;
	
	@UpdateTimestamp
	private LocalDateTime updateTimestamp;
	
	/**@OneToMany(mappedBy = "category")
    private List<Sale> sale = new ArrayList<>();**/
	
	@Builder
	public Category(String name, String description, Integer id)
	{
		this.name=name;
		this.description=description;
		this.id=id;
	}

}
