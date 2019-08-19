import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";
import { Product } from "./Product";
import { Order } from "./Order";

@Entity("lineitems")
export class Lineitem {
    @PrimaryGeneratedColumn()
    id: number

    @Column()
    quantity: number

    @Column()
    price: number

    @ManyToOne(type => Product, product => product.lineitems)
    @JoinColumn({name: "product_id"})
    product: Product

    @ManyToOne(type => Order, order => order.lineitems)
    @JoinColumn({name: "order_id"})
    order: Order
}