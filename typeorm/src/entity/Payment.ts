import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";
import { Order } from "./Order";

@Entity("payments")
export class Payment {
    @PrimaryGeneratedColumn()
    id: number

    @Column()
    paid: boolean

    @Column()
    refunded: boolean

    @Column()
    amount: number

    @ManyToOne(type => Order, order => order.payments)
    @JoinColumn({name: "order_id"})
    order: Order
}