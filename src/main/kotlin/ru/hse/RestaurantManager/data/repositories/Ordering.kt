package ru.hse.RestaurantManager.data.repositories

import jakarta.persistence.*
import lombok.Data
import lombok.ToString
import java.util.*

@Data
@Entity
@Table(name = "ordering")
class Ordering {
    @Id
    @Column(name = "id")
    private var id: UUID? = null

    @Column(name = "name")
    private var name: String? = null

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "client_ordering",
        joinColumns = [JoinColumn(name = "client_id")],
        inverseJoinColumns = [JoinColumn(name = "ordering_id")]
    )
    private val clients: List<Client>? = null
}