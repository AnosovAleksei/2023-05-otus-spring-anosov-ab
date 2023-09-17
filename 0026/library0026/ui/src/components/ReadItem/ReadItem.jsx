import { useState, useEffect } from "react";
import Select from "react-select";
import {Card} from "react-bootstrap";

export const ReadItem = ({data, itemName, columnNameArray, columnKeyArray, url}) => { 

    const [dataL, setData] = useState(null);

    let options = null;
    
    if(!!data && !!data[itemName]){
        options = data[itemName].map((item) => ({['value']: item.id, ['label']: item.id}));
        console.log(options);
    }
    
    const onSelect = (id) => {
        console.log(id);
        fetch(`${url}${id}`)
        .then(response => response.json())
        .then(item => setData({item}))
       };

    
    return (<Card>
                {!!options &&
                    <Select options={options}
                    placeholder = {"id"}
                    
                    onChange={opt => onSelect(opt.value)}
                    // styles={customStyles}
                    />

                }
                <table >
                    <thead>
                    <tr>
                        {columnNameArray.map((n, i)=> (<th key={n+i}>{n}</th>))}
                    </tr>
                    </thead>
                    <tbody>
                    { !!dataL && !!dataL.item &&
                        
                            <tr  key={0}>
                                {columnKeyArray.map((k, i)=> (<td key={k+i}>{dataL.item[k]}</td>))}
                            </tr>
                        
                    }
                    </tbody>
                </table>
    </Card>)
}
