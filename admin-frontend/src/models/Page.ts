import {Direction} from "@models/Sort.ts";

export interface PageRequest{
    page: number;
    size: number;
    direction: Direction;
    properties: string []

}

export interface SortRequest{
    direction: Direction;
    properties: string []
}

export interface Page<T>{
    page: number;
    size: number;
    total: number;
    content: T[];
}