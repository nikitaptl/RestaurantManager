package ru.hse.RestaurantManager.data.repositories

import jakarta.persistence.*
import lombok.Data
import lombok.ToString
import java.util.*

@Data
@Entity
@Table(name = "client")
class Client {
    @Id
    @Column(name = "id")
    private var id: UUID? = null

    @Column(name = "name")
    private var name: String? = null

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    private val authors: List<Ordering>? = null
}