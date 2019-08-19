import {MigrationInterface, QueryRunner} from "typeorm";

export class CreateAllAssignmentTables1566192303626 implements MigrationInterface {

    public async up(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`CREATE TABLE "products" ("id" int NOT NULL IDENTITY(1,1), "name" nvarchar(255) NOT NULL, "price" int NOT NULL, CONSTRAINT "PK_0806c755e0aca124e67c0cf6d7d" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "payments" ("id" int NOT NULL IDENTITY(1,1), "paid" bit NOT NULL, "refunded" bit NOT NULL, "amount" int NOT NULL, "order_id" int, CONSTRAINT "PK_197ab7af18c93fbb0c9b28b4a59" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "orders" ("id" int NOT NULL IDENTITY(1,1), CONSTRAINT "PK_710e2d4957aa5878dfe94e4ac2f" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "lineitems" ("id" int NOT NULL IDENTITY(1,1), "quantity" int NOT NULL, "price" int NOT NULL, "product_id" int, "order_id" int, CONSTRAINT "PK_255b0947084102de4dfdcff1596" PRIMARY KEY ("id"))`);
        await queryRunner.query(`ALTER TABLE "payments" ADD CONSTRAINT "FK_b2f7b823a21562eeca20e72b006" FOREIGN KEY ("order_id") REFERENCES "orders"("id") ON DELETE NO ACTION ON UPDATE NO ACTION`);
        await queryRunner.query(`ALTER TABLE "lineitems" ADD CONSTRAINT "FK_1cbb8668144f8459bcbdd2b6b82" FOREIGN KEY ("product_id") REFERENCES "products"("id") ON DELETE NO ACTION ON UPDATE NO ACTION`);
        await queryRunner.query(`ALTER TABLE "lineitems" ADD CONSTRAINT "FK_e397e37ba6d931ac2c969799ef5" FOREIGN KEY ("order_id") REFERENCES "orders"("id") ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<any> {
        await queryRunner.query(`ALTER TABLE "lineitems" DROP CONSTRAINT "FK_e397e37ba6d931ac2c969799ef5"`);
        await queryRunner.query(`ALTER TABLE "lineitems" DROP CONSTRAINT "FK_1cbb8668144f8459bcbdd2b6b82"`);
        await queryRunner.query(`ALTER TABLE "payments" DROP CONSTRAINT "FK_b2f7b823a21562eeca20e72b006"`);
        await queryRunner.query(`DROP TABLE "lineitems"`);
        await queryRunner.query(`DROP TABLE "orders"`);
        await queryRunner.query(`DROP TABLE "payments"`);
        await queryRunner.query(`DROP TABLE "products"`);
    }

}
