import { Entity, PrimaryGeneratedColumn, OneToMany } from "typeorm";
import { Lineitem } from "./Lineitem";
import { Payment } from "./Payment";

@Entity("orders")
export class Order {
    @PrimaryGeneratedColumn()
    id: number

    @OneToMany(type => Lineitem, lineitem => lineitem.order)
    lineitems: Lineitem[]

    @OneToMany(type => Payment, payment => payment.order)
    payments: Payment[]
}