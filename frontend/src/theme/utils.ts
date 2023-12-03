import chroma from "chroma-js";

const shadow = (offset = [], radius = [], color: string, opacity:number, inset = "") : string => {
    const [x, y] = offset;
    const [blur, spread] = radius;

    return `${inset} ${pxToRem(x)} ${pxToRem(y)} ${pxToRem(blur)} ${pxToRem(spread)} ${rgba(
        color,
        opacity
    )}`;
}
function rgba(color: string, opacity:number) {
    return `rgba(${hexToRgb(color)}, ${opacity})`;
}

function hexToRgb(color: string) {
    return chroma(color).rgb().join(", ");
}

function pxToRem(number: number, baseNumber: number = 16) {
    return `${number / baseNumber}rem`;
}

export {shadow, rgba, pxToRem, hexToRgb}

