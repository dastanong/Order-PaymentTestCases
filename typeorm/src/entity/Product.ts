import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm";
import { Lineitem } from "./Lineitem";

@Entity("products")
export class Product {
    @PrimaryGeneratedColumn()
    id: number
    
    @Column()
    name: string

    @Column()
    price: number

    @OneToMany(type => Lineitem, lineitem => lineitem.product)
    lineitems: Lineitem[]

}