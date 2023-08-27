import { useState, useEffect } from "react";
import Select from "react-select";
import {Card} from "react-bootstrap";
import Button from 'react-bootstrap/Button';

export const DeleteItem = ({data, loadAllData, itemName, columnNameArray, columnKeyArray, url}) => { 

    const [dataL, setData] = useState(null);
    const [statusDelete, setStatusData] = useState(null);

    let options = null;
    
    if(!!data && !!data[itemName]){
        options = data[itemName].map((item) => ({['value']: item.id, ['label']: item.id}));
        console.log(options);
    }
    
    const onSelect = (id) => {
        
        fetch(`${url}${id}`)
        .then(response => response.json())
        .then(item => setData({item}))
       };

    const onDelete = () => {
        if(!!dataL && !!dataL.item){
            const id = dataL.item.id;
            fetch(`/api/v1/book/${id}`, 
                {
                    method: "DELETE", // *GET, POST, PUT, DELETE, etc.
                    mode: "cors", // no-cors, *cors, same-origin
                    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                    credentials: "same-origin", // include, *same-origin, omit
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    redirect: "follow", // manual, *follow, error
                    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                    body: JSON.stringify({"id": dataL.item.id}), // body data type must match "Content-Type" header
                }
            
            
            )
            .then(response => {
                setStatusData(response.statusText);
                loadAllData();
            })
        }
    };

    
    return (<Card>
                {!!options &&
                    <Select options={options}
                    placeholder = {"id"}
                    
                    onChange={opt => onSelect(opt.value)}

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
                <Button variant="danger" onClick={onDelete}>удалить</Button>
                {!!statusDelete && <p>htth status: {statusDelete}</p>}
    </Card>)
}
