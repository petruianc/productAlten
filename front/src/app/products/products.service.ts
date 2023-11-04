import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from './product.class';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    public static productslist: Product[] = null;
    public products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);
    private baseUrl: string = 'http://localhost:8080/products';

    constructor(private http: HttpClient) { }

    getProducts(): Observable<Product[]> {
    if( ! ProductsService.productslist )
        {
        this.http.get<any>(this.baseUrl).subscribe(data => {
            ProductsService.productslist = data.data;
            this.products$.next(ProductsService.productslist);
        });
    }
    else
    {
        this.products$.next(ProductsService.productslist);
    } 
        return this.products$;
    }


    create(prod: Product): Observable<Product[]> {
        console.log(prod);
        this.http.post<Product>(this.baseUrl, prod).subscribe(
            res=>{
                ProductsService.productslist.push(res);
                this.products$.next(ProductsService.productslist);     
            }
        );
        return this.products$;
    }

    update(prod: Product): Observable<Product[]>{
        ProductsService.productslist.forEach(element => {
            if(element.id == prod.id)
            {
                element.name = prod.name;
                element.category = prod.category;
                element.code = prod.code;
                element.description = prod.description;
                element.image = prod.image;
                element.inventoryStatus = prod.inventoryStatus;
                element.price = prod.price;
                element.quantity = prod.quantity;
                element.rating = prod.rating;
            }
        });
        this.http.patch<Product>(this.baseUrl+`/${prod.id}`, prod).subscribe(
            data=>{
                this.products$.next(ProductsService.productslist);
            }
        );
        console.log(prod);
        return this.products$;
    }


    delete(id: number): Observable<Product[]>{

        this.http.delete<string>(this.baseUrl+`/${id}`, { responseType: 'text' as 'json' }).subscribe(
            data => {
                ProductsService.productslist = ProductsService.productslist.filter(value => { return value.id !== id } );
                this.products$.next(ProductsService.productslist);
            }
        );
        console.log(id);
        return this.products$;
    }
}