export interface Sort {
    direction?: Direction;
    property?: string;
}

export enum Direction {
    DESC = "DESC",
    ASC = "ASC"
}

export interface Page{
    sort: Sort;
    page: number;
    limit: number;
}