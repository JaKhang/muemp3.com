import { SvgIcon } from "@mui/material"

export default interface MenuItem{
    id: string
    url: string
    icon?: typeof SvgIcon
    title: string
    type: MenuItemType
    target?: boolean
    external?: boolean
    children?: MenuItem[] 
}

export enum MenuItemType{
    ITEM = 'item',
    GROUP = 'group',
    COLLAPSE = 'collapse'
}

