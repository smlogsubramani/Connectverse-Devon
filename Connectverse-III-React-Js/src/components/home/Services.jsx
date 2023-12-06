// import JsonData from "../../data/index.json";

const Services = ({ Services }) => {

  return (
    <div id='services' className='text-center'>
      <div className='container'>
        <div className='section-title'>
          <h2>{ Services.tittle }</h2>
          <p>
            { Services.subTittle }
          </p>
        </div>
        <div className='row'>
          {Services
            ? Services.Services.map((d, i) => (
                <div key={`${d.name}-${i}`} className='col-md-4'>
                  {' '}
                  <i className={d.icon}></i>
                  <div className='service-desc'>
                    <h3>{d.name}</h3>
                    <p>{d.text}</p>
                  </div>
                </div>
              ))
            : 'loading'}
        </div>
      </div>
    </div>
  )
}

export default Services;
